import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { IngestionRequestDetailsDetailComponent } from 'app/entities/ingestion-request-details/ingestion-request-details-detail.component';
import { IngestionRequestDetails } from 'app/shared/model/ingestion-request-details.model';

describe('Component Tests', () => {
  describe('IngestionRequestDetails Management Detail Component', () => {
    let comp: IngestionRequestDetailsDetailComponent;
    let fixture: ComponentFixture<IngestionRequestDetailsDetailComponent>;
    const route = { data: of({ ingestionRequestDetails: new IngestionRequestDetails(123) }) } as any as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [IngestionRequestDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IngestionRequestDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IngestionRequestDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ingestionRequestDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
