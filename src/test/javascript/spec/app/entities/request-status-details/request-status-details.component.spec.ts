import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IngestionApiTestModule } from '../../../test.module';
import { RequestStatusDetailsComponent } from 'app/entities/request-status-details/request-status-details.component';
import { RequestStatusDetailsService } from 'app/entities/request-status-details/request-status-details.service';
import { RequestStatusDetails } from 'app/shared/model/request-status-details.model';

describe('Component Tests', () => {
  describe('RequestStatusDetails Management Component', () => {
    let comp: RequestStatusDetailsComponent;
    let fixture: ComponentFixture<RequestStatusDetailsComponent>;
    let service: RequestStatusDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [RequestStatusDetailsComponent],
        providers: [],
      })
        .overrideTemplate(RequestStatusDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RequestStatusDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RequestStatusDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RequestStatusDetails(123)],
            headers,
          }),
        ),
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.requestStatusDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
