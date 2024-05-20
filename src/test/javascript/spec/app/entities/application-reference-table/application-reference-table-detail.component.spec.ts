import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { ApplicationReferenceTableDetailComponent } from 'app/entities/application-reference-table/application-reference-table-detail.component';
import { ApplicationReferenceTable } from 'app/shared/model/application-reference-table.model';

describe('Component Tests', () => {
  describe('ApplicationReferenceTable Management Detail Component', () => {
    let comp: ApplicationReferenceTableDetailComponent;
    let fixture: ComponentFixture<ApplicationReferenceTableDetailComponent>;
    const route = { data: of({ applicationReferenceTable: new ApplicationReferenceTable(123) }) } as any as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [ApplicationReferenceTableDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ApplicationReferenceTableDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationReferenceTableDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applicationReferenceTable).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
