import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IngestionApiTestModule } from '../../../test.module';
import { TechnicalDetailsComponent } from 'app/entities/technical-details/technical-details.component';
import { TechnicalDetailsService } from 'app/entities/technical-details/technical-details.service';
import { TechnicalDetails } from 'app/shared/model/technical-details.model';

describe('Component Tests', () => {
  describe('TechnicalDetails Management Component', () => {
    let comp: TechnicalDetailsComponent;
    let fixture: ComponentFixture<TechnicalDetailsComponent>;
    let service: TechnicalDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [TechnicalDetailsComponent],
        providers: [],
      })
        .overrideTemplate(TechnicalDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TechnicalDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TechnicalDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TechnicalDetails(123)],
            headers,
          }),
        ),
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.technicalDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
