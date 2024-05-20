import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IngestionApiTestModule } from '../../../test.module';
import { IngestionRequestDetailsComponent } from 'app/entities/ingestion-request-details/ingestion-request-details.component';
import { IngestionRequestDetailsService } from 'app/entities/ingestion-request-details/ingestion-request-details.service';
import { IngestionRequestDetails } from 'app/shared/model/ingestion-request-details.model';

describe('Component Tests', () => {
  describe('IngestionRequestDetails Management Component', () => {
    let comp: IngestionRequestDetailsComponent;
    let fixture: ComponentFixture<IngestionRequestDetailsComponent>;
    let service: IngestionRequestDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [IngestionRequestDetailsComponent],
        providers: [],
      })
        .overrideTemplate(IngestionRequestDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IngestionRequestDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IngestionRequestDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IngestionRequestDetails(123)],
            headers,
          }),
        ),
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ingestionRequestDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
